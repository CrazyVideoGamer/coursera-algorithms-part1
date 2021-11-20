FOLDER = perc
TARGETS = PercolationStats
ARGS = 200 100

TARGETS_EXT = $(addsuffix .java, $(TARGETS))
TARGETS_FULL = $(patsubst %, $(FOLDER)/%.java, $(TARGETS))

run: $(TARGETS_FULL)
	cd $(FOLDER) && java $(TARGETS_EXT) $(ARGS)